require "securerandom"
require "sinatra"
require "content-type"
require "./file_store"

set :port, 5000
set :show_exceptions, :after_handler
$stdout.sync = true

store = FileStore.new("data")

get "/todo" do
  filter = params[:filter] || ""
  tasks = store.list(request.path)
    .map { |url| [url, store.get(url)] }
    .select { |url, task| task[:task].include? filter }

  erb(:todo_listing, locals: { tasks: tasks, filter: filter })
end

get "/todo/:id" do
  task = store.get(request.path)
  erb(:todo_detail, locals: { url: request.path, task: task })
end

get "/todo/:id/edit" do
  subjectId = "/todo/#{params[:id]}"
  task = store.get(subjectId)
  erb(:todo_edit, locals: { url: subjectId, task: task })
end

put "/todo/:id" do
  media_type = ContentType.parse request.media_type
  error 415 unless media_type.mime_type === "application/x-www-form-urlencoded"

  unless params.key? :task
    error 400, erb(:"400_bad_request", locals: {
      errors: ["<code>task</code> is a required field"]
    })
  end

  unless params[:task].length > 0
    error 400, erb(:"400_bad_request", locals: {
      errors: ["<code>task</code> cannot be empty"]
    })
  end

  task = {
    task: params[:task],
    completed: params[:completed] === "on"
  }
  store.set(request.path, task)

  erb(:todo_detail, locals: { url: request.path, task: task })
end

delete "/todo/:id" do
  store.remove(request.path)

  halt 204
end

post "/todo" do
  media_type = ContentType.parse request.media_type
  error 415 unless media_type.mime_type === "application/x-www-form-urlencoded"

  unless params.key? :task
    error 400, erb(:"400_bad_request", locals: {
      errors: ["<code>task</code> is a required field"]
    })
  end

  unless params[:task].length > 0
    error 400, erb(:"400_bad_request", locals: {
      errors: ["<code>task</code> cannot be empty"]
    })
  end

  task = {
    task: params[:task],
    completed: false
  }

  id = "/todo/#{SecureRandom.uuid}"
  store.set(id, task)

  status 201
  headers "Location" => id
  erb(:todo_created, locals: { location: id })
end

post "/todo/:id/toggle" do
  subjectId = "/todo/#{params["id"]}"
  task = store.get(subjectId)
  task[:completed] = (not task[:completed])
  store.set(subjectId, task)

  redirect subjectId, 303
end

error FileStore::NoSuchResource do
  status 404
  erb(:"404_not_found")
end

error 404 do
  erb(:"404_not_found")
end

error 406 do
  erb(:"406_not_acceptable")
end

error 415 do
  erb(:"415_unsupported_media_type")
end

error 500 do
  erb(:"500_internal_server_error")
end

extensionFor = {
  "text/html" => "html",
  "text/css" => "css"
}

get "*" do
  error 404 if Dir.glob("web#{request.path}.*").empty?

  request.accept.each do |type|
    extension = extensionFor[type.to_s]

    if extensionFor.key?(type.to_s)
      filePath = "web#{request.path}.#{extension}"

      if File.exists? filePath
        halt send_file(filePath)
      end
    end
  end

  error 406
end

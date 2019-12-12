class FileStore
  class NoSuchResource < RuntimeError
  end

  def initialize(path)
    @path = path
  end

  def get(id)
    filename = "#{@path}#{id}.json"
    raise NoSuchResource unless File.exists? filename

    task = File.read(filename)
    JSON.parse(task, symbolize_names: true)
  end

  def list(id)
    dirname = "#{@path}#{id}"
    raise NoSuchResource unless Dir.exists? dirname

    Dir["#{dirname}/*"].map do |filename|
      filename.delete_prefix! @path
      filename.delete_suffix! ".json"
      filename
    end
  end

  def set(id, task)
    filename = "#{@path}#{id}.json"
    file = File.open(filename, "w")
    file.puts JSON.pretty_generate(task)
    file.close
  end

  def remove(id)
    filename = "#{@path}#{id}.json"
    File.delete(filename) if File.exists? filename
  end
end

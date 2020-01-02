module Shared
  module Standard
    include Spinach::DSL

    step "my response should have status code 200" do
      expect(@response.status_code).to eq 200
    end

    step "my response should have status code 201" do
      expect(@response.status_code).to eq 201
    end

    step "my response should have status code 204" do
      expect(@response.status_code).to eq 204
    end

    step "my response should have status code 303" do
      expect(@response.status_code).to eq 303
    end

    step "my response should have status code 400" do
      expect(@response.status_code).to eq 400
    end

    step "my response should have status code 404" do
      expect(@response.status_code).to eq 404
    end

    step "my response should have status code 415" do
      expect(@response.status_code).to eq 415
    end

    step "my response should return html" do
      content_type = @response.content_type
      expect(content_type.mime_type).to eq "text/html"
      expect(content_type.charset).to eq "utf-8"
    end

    step "my response should have a non-empty body" do
      expect(@response.body.length).to be > 0
    end

    step "my response should have an empty body" do
      expect(@response.body.length).to eq 0
    end

    step "my response should have Content-Length greater than 0" do
      expect(@response.content_length).to be > 0
    end
  end
end

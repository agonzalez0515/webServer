require "./features/steps/shared.rb"

class Spinach::Features::PageNotFound < Spinach::FeatureSteps
  include Shared::Standard

  step "I make a GET request to a page that doesn't exist" do
    @response = Requests.get("/not_found_resource")
  end

  step "my response should return html" do
    content_type = @response.content_type
    expect(content_type.mime_type).to eq "text/html"
    expect(content_type.charset).to eq "utf-8"
  end
end

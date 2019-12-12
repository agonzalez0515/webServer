require "uri"
require "./features/steps/shared.rb"

class Spinach::Features::CreateATodoItem < Spinach::FeatureSteps
  include Shared::Standard

  step "I make a valid POST request to the listing page" do
    @response = Requests.post("/todo", "task=a%20new%20task", {
      "Content-Type": "application/x-www-form-urlencoded"
    })
  end

  step "my response should have a Location header pointing to a detail page" do
    location = URI(@response.location).path
    expect(location).to match(/^\/todo\/[-\w]+$/)

    created = Requests.get(location)
    expect(created.status_code).to eq 200
  end

  step "I make a POST request with an unsupported media type to the listing page" do
    @response = Requests.post("/todo", "<task>a new task</task>", {
      "Content-Type": "text/xml; charset=utf-8"
    })
  end

  step "I make a POST request with invalid values to the listing page" do
    @response = Requests.post("/todo", "a new task", {
      "Content-Type": "application/x-www-form-urlencoded"
    })
  end
end

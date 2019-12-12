require "uri"
require "./features/steps/shared.rb"

class Spinach::Features::ToggleTodoItemCompletedFlag < Spinach::FeatureSteps
  include Shared::Standard

  step "I make a valid POST request to an unfinished todo item" do
    @response = Requests.post("/todo/2/toggle", "", {
      "Content-Type": "application/x-www-form-urlencoded"
    })
  end

  step "my response should have a Location header pointing to the detail page" do
    location = URI(@response.location).path
    expect(location).to eq("/todo/2")
  end

  step "the resource should be marked as completed" do
    updated = Nokogiri::HTML(Requests.get("/todo/2").body)
    expect(updated.css(".complete").length).to eq(1)
  end

  step "I make a valid POST request to a completed todo item" do
    @response = Requests.post("/todo/2/toggle", "", {
      "Content-Type": "application/x-www-form-urlencoded"
    })
  end

  step "the resource should be marked as unfinished" do
    updated = Nokogiri::HTML(Requests.get("/todo/2").body)
    expect(updated.css(".incomplete").length).to eq(1)
  end
end

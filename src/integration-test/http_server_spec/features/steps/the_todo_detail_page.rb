require "nokogiri"
require "./features/steps/shared.rb"

class Spinach::Features::TheTodoDetailPage < Spinach::FeatureSteps
  include Shared::Standard

  step "I make a GET request to a todo detail page" do
    @response = Requests.get("/todo/1")
  end

  step "the body should contain a link to the TODO item listing page" do
    page = Nokogiri::HTML(@response.body)
    expect(page.css("a[rel=collection]").length).to be > 0
  end
end

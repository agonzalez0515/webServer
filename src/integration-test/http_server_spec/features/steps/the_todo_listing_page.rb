require "nokogiri"
require "./features/steps/shared.rb"

class Spinach::Features::TheTodoListingPage < Spinach::FeatureSteps
  include Shared::Standard

  step "I make a GET request to the listing page" do
    @response = Requests.get("/todo")
  end

  step "each item listed should contain a link to a TODO item detail page" do
    page = Nokogiri::HTML(@response.body)
    expect(page.css("a[rel=item]").length).to be > 0
  end
end

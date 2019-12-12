require "./features/steps/shared.rb"

class Spinach::Features::FilterTheListingPage < Spinach::FeatureSteps
  include Shared::Standard

  step "I make a GET request to the listing page with a 'filter' query param" do
    @response = Requests.get("/todo?filter=do%20a%20thing")
  end

  step "my response body should contain only the items that contain the text in the filter" do
    page = Nokogiri::HTML(@response.body)
    expect(page.css("a[rel=item]").length).to eq 1
  end
end

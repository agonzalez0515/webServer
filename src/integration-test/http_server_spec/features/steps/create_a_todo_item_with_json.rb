require "./features/steps/shared.rb"

class Spinach::Features::CreateATodoItemWithJson < Spinach::FeatureSteps
  include Shared::Standard

  step "I make a valid POST request to the listing page with JSON" do
    @response = Requests.post("/todo", '{"task":"a new task"}', {
      "Content-Type": "application/json"
    })
  end

  step "my response should have a Location header pointing to a detail page" do
    pending "step not implemented"
  end
end

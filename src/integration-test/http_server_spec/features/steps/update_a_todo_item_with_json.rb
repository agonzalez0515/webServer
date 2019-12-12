require "./features/steps/shared.rb"

class Spinach::Features::UpdateATodoItemWithJson < Spinach::FeatureSteps
  include Shared::Standard

  step "I make a valid PUT request to a TODO item with JSON accepting JSON" do
    @response = Requests.put("/todo/3", '{"task":"an updated task"}', {
      "Content-Type": "application/json",
      "Accepts": "application/json"
    })
  end

  step "my response should be the same representation as the request" do
    pending "step not implemented"
  end

  step "reteiving the resource as JSON should be the same representation as the request" do
    pending "step not implemented"
  end

  step "I make a valid PUT request to a TODO item with JSON accepting HTML" do
    @response = Requests.put("/todo/3", '{"task":"an updated task"}', {
      "Content-Type": "application/json",
      "Accepts": "text/html"
    })
  end

  step "I make a valid PUT request to a TODO item with JSON accepting XML" do
    @response = Requests.put("/todo/3", '{"task":"an updated task"}', {
      "Content-Type": "application/json",
      "Accepts": "text/xml"
    })
  end
end

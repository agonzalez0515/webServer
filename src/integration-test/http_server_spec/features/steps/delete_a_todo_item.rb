require "./features/steps/shared.rb"

class Spinach::Features::DeleteATodoItem < Spinach::FeatureSteps
  include Shared::Standard

  step "I make a DELETE request to a TODO item" do
    @response = Requests.delete("/todo/3")
  end

  step "I make a DELETE request to a TODO item that doesn't exist" do
    @response = Requests.delete("/todo/doesnotexist")
  end
end

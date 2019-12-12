require "./features/steps/shared.rb"

class Spinach::Features::HealthCheck < Spinach::FeatureSteps
  include Shared::Standard

  step "I make a GET request to '/health-check'" do
    @response = Requests.get("/health-check", { "Accept": "text/html" })
  end

  step "I make a HEAD request to '/health-check'" do
    @response = Requests.head("/health-check", { "Accept": "text/html" })
  end
end

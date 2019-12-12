require "httparty"

module Requests
  def self.get(path, headers = {})
    base_url = "#{PROTOCOL}://#{HOSTNAME}:#{PORT}"
    Response.new(HTTParty.get("#{base_url}#{path}", headers: headers, follow_redirects: false))
  end

  def self.head(path, headers = {})
    base_url = "#{PROTOCOL}://#{HOSTNAME}:#{PORT}"
    Response.new(HTTParty.head("#{base_url}#{path}", headers: headers, follow_redirects: false))
  end

  def self.post(path, body="", headers = {})
    base_url = "#{PROTOCOL}://#{HOSTNAME}:#{PORT}"
    Response.new(HTTParty.post("#{base_url}#{path}", body: body, headers: headers, follow_redirects: false))
  end

  def self.put(path, body="", headers = {})
    base_url = "#{PROTOCOL}://#{HOSTNAME}:#{PORT}"
    Response.new(HTTParty.put("#{base_url}#{path}", body: body, headers: headers, follow_redirects: false))
  end

  def self.delete(path, headers = {})
    base_url = "#{PROTOCOL}://#{HOSTNAME}:#{PORT}"
    Response.new(HTTParty.delete("#{base_url}#{path}", headers: headers, follow_redirects: false))
  end
end

#!/bin/bash

cd src/integration-test/http_server_spec
bundle install
bundle exec spinach --tags @0_file_server,@1_forms,@2_idempotent_unsafe
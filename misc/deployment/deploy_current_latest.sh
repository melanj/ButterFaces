#!/bin/bash
# Loads latest docker image, stops old version and starts latest version

echo "### Start:       Pull latest docker image"
sudo docker pull butterfaces/showcase
echo "### Finished:    Pull latest docker image"

echo "### Start:       Stopping actual running application"
sudo docker stop butterfaces-showcase
echo "### Finished:    Stopping actual running application"

echo "### Start:       Removing old docker image"
sudo docker rm butterfaces-showcase
echo "### Finished:    Removing old docker image"

echo "### Start:       Starting application"
sudo docker run -d --restart always -p 8084:8080 --name butterfaces-showcase butterfaces/showcase
echo "### Finished:    Removing application"
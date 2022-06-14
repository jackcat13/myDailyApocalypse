# Start Linux Github runner

```sh
docker run -d --restart always --name github-runner \
  -e REPO_URL="https://github.com/jackcat13/myDailyApocalypse" \
  -e RUNNER_NAME="linux-runner" \
  -e ACCESS_TOKEN="anyToken" \
  -e RUNNER_WORKDIR="/tmp/github-runner-your-repo" \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v /tmp/github-runner-your-repo:/tmp/github-runner-your-repo \
myoung34/github-runner:latest
```
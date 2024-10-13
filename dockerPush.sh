services=("eureka" "gateway" "auth" "user")
for service in "${services[@]}"
do
  imageName="$service"
  # AWS ECR에 Push
  docker push "$DOCKER_HUB_PATH/$imageName"

  echo "$service image is built and pushed to AWS ECR"
done
echo "Build and Push processing is done"

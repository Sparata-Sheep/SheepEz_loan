services=("eureka" "gateway" "auth" "user" "lawyer" "contact")
for service in "${services[@]}"
do
  imageName="$service"
  # AWS ECR에 Push
  docker push "$DOCKER_HUB_USERNAME/$imageName"

  echo "$service image is built and pushed to AWS ECR"
done
echo "Build and Push processing is done"

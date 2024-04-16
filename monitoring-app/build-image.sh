mvn clean package
podman build -f src/main/docker/Dockerfile.jvm -t quarkus/train-monitoring-app-jvm --platform linux/arm64/v8 . 
podman tag quarkus/train-monitoring-app-jvm:latest quay.io/mouachan/train-monitoring-app-arm64:latest    
podman push quay.io/mouachan/train-monitoring-app-arm64:latest 
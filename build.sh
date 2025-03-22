export JAVA_HOME=/opt/render/project/.render/java-17
export PATH=$JAVA_HOME/bin:$PATH
chmod +x mvnw
./mvnw clean package

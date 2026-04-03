# Multi-Threaded Web Server From Scratch in Java

A Multi-threading HTTP/1.1 web server built from scratch in Java, featuring parsing of Request and Response, Serving Static media files and handling end-point routing. 

### Core Components

```
src/main/java/
├── App.java                    # Main server entry point
├── Controller/                 # Request handlers
│   ├── HomeController.java     # Basic endpoints
│   ├── AuthController.java     # Authentication
│   └── MediaController.java    # Media file operations
└── Model/                      
    ├── HttpRequestWrapper.java  # HTTP request parser
    ├── HttpResponseWrapper.java # HTTP response builder
    └── Task.java               # Request processing task
```

## Quick Setup
```bash
#Running the java application using .jar file
java -jar WebServer.jar
```

## Compiling and Building Project
```bash
# Navigate to project directory
cd webserver-from-scratch-java

# Compile source files
javac -cp "target" -d "target" src/main/java/Model/HttpRequestWrapper.java

javac -cp "target" -d "target" src/main/java/Model/HttpResponseWrapper.java

javac -cp "target" -d "target" src/main/java/Controller/*.java

javac -cp "target" -d "target" src/main/java/Model/Task.java

javac -cp "target" -d "target" src/main/java/App.java

# Run the server
java -cp "target;src/main/java/Resources" src/main/java/App.java
```

## Packaging the Java Application
```bash
jar cfe WebServer App.class -c target . src\main\java\Resources
```

## WebServer End-Points Description

| Endpoint | HTTP Method | Request Body/Parameters | Description |
|----------|-------------|------------------------|-------------|
| `/helloworld` | GET, HEAD | None | Returns a simple "Hello World" text message. HEAD method returns only headers. |
| `/serverstatus` | GET | Accept header (optional: `text/plain` or `text/html`) | Returns server status message. Supports content negotiation via Accept header to return either plain text or HTML response. |
| `/images` | GET | None | Retrieves the default image file |
| `/images` | POST | Base64-encoded image data in request body | Uploads an image file. Returns 201 Created status. |
| `/videos` | GET | None | Retrieves the default video file |
| `/videos` | POST | Base64-encoded video data in request body | Uploads a video file. Returns 201 Created status. |
| `/audio` | GET | None | Retrieves the default audio file |
| `/audio` | POST | Base64-encoded audio data in request body | Uploads an audio file. Returns 201 Created status. |




## Load / Peformance Testing
Kindly go through [here](LoadTests/LoadTest_ReadMe.md) for complete description of Load Tests that are performed.

performed load testing using [Locust](https://locust.io/):

```bash
cd webserver-from-scratch-java

# creating & activating virtual environment for installing locust library
python -m venv "LoadTestEnv"  
LoadTestEnv\Scripts\Activate.ps1 

#installing locust library
pip install locust 

# Run uniform load test
locust -f LoadTests\UniformLoadTesting.py --config=UniformLoadTestConfig.conf

# Run media-intensive test  
locust -f LoadTests\MediaIntensiveLoadTesting.py --config=MediaIntensiveLoadTestConfig.conf

# Run mixed workload test
locust -f LoadTests\MixedLoadTesting.py --config=MixedLoadTestConfig.conf
```
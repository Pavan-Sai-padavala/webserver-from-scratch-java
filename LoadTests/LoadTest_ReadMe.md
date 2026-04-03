# Load / Performace Testing using [Locust](https://locust.io/)

## Overview

The load tests are designed to evaluate the performance and reliability of the web server under different scenarios:

- **Uniform Load Testing**: Normal Test User, making Requests in Equal Ratio on all endpoints
- **Media Intensive Testing**: Media Intensive Test User,making heavy requests on media-heavy endpoints (images, audio, video)  
- **Mixed Load Testing**: Combining Normal Test User and Media Intensive Test User

## Quick Setup

```bash
cd webserver-from-scratch-java

# creating & activating virtual environment for installing locust library
python -m venv "LoadTestEnv"  
LoadTestEnv\Scripts\Activate.ps1 

#installing locust library
pip install locust
```

## Test Scenario and Test Script Description

### 1. Uniform Load Testing (`UniformLoadTesting.py`)
Tests all endpoints with equal weight distribution:
- **Target End-points**: All server endpoints (`/helloworld`, `/images`, `/audio`, `/videos`, `/serverstatus`)

```bash
# Run uniform load test
locust -f LoadTests\UniformLoadTesting.py --config=UniformLoadTestConfig.conf
```

- **Results**: `LoadTests/UniformLoadTestResults/`

### 2. Media Intensive Testing (`MediaIntensiveLoadTesting.py`)
Focuses on high-bandwidth media endpoints:
- **Target End-points**: Media endpoints (`/images`, `/audio`, `/videos`)

```bash
# Run media-intensive test  
locust -f LoadTests\MediaIntensiveLoadTesting.py --config=MediaIntensiveLoadTestConfig.conf
```

- **Results**: `LoadTests/MediaIntensiveLoadTestResults/`

### 3. Mixed Load Testing (`MixedLoadTesting.py`)
Combines both normal and media-intensive user behaviors:
- **Target**:  All server endpoints (`/helloworld`, `/images`, `/audio`, `/videos`, `/serverstatus`)
- **Strategy**: Requests sending rate for:
                Uniform Test User:- 25% and, 
                Media Intensive Test User:- 75%

```bash
# Run mixed workload test
locust -f LoadTests\MixedLoadTesting.py --config=MixedLoadTestConfig.conf
```

- **Results**: `LoadTests\MixedLoadTestResults/`


### Web UI Dashboard for Test Monitoring
- **Uniform Test**: http://localhost:8091
- **Media Intensive Test**: http://localhost:8090  
- **Mixed Test**: http://localhost:8089
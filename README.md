# Requests.java Library

![GitHub stars](https://img.shields.io/github/stars/farhanaliofficial/Requests.java?style=social)
![GitHub followers](https://img.shields.io/github/followers/farhanaliofficial?style=social)

Author: [Farhan Ali](https://github.com/farhanaliofficial)

**Version:** 1.0.0

### Overview

Requests is a lightweight and purely sockets-based Java HTTP client library designed for making HTTP and HTTPS requests with ease and reliability. It provides built-in methods for common HTTP operations and robust error handling.

### Features

- Supports HTTP and HTTPS requests.
- Simplifies GET and POST requests with user-friendly methods.
- Provides robust error handling for different situations.
- Easy-to-use for making HTTP requests in Java applications.

### Usage

#### Creating Requests Object

```java
Requests requests = new Requests();
```

#### Making a GET Request

```java
String url = "https://example.com";
RequestsResponse response = requests.get(url);
```

#### Making a GET Request with Custom Headers

```java
String url = "https://example.com";
Map<String, String> headers = new HashMap<>();
headers.put("User-Agent", "MyApp/1.0");
RequestsResponse response = requests.get(url, headers);
```

#### Making a POST Request

```java
String url = "https://example.com";
Map<String, String> data = new HashMap<>();
data.put("param1", "value1");
data.put("param2", "value2");
RequestsResponse response = requests.post(url, data);
```

#### Making a POST Request with Custom Headers

```java
String url = "https://example.com";
Map<String, String> headers = new HashMap<>();
headers.put("Authorization", "Bearer Token");
Map<String, String> data = new HashMap<>();
data.put("param1", "value1");
RequestsResponse response = requests.post(url, headers, data);
```

### GitHub Repository

Find the latest updates and contribute to the project on [GitHub](https://github.com/farhanaliofficial/Requests.java).

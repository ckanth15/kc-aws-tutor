# KC Cloud Tutor

A **cost-optimized** cloud computing tutorial platform built with **Java Spring Boot**, **Docker**, and **AWS** - demonstrating full-stack development, DevOps, and cloud engineering skills.

## 🚀 Technologies Demonstrated

### Backend
- **Java 11** - Enterprise-grade backend development
- **Spring Boot 2.7** - Microservices and web application framework
- **Thymeleaf** - Server-side template engine
- **Maven** - Dependency management and build automation

### DevOps & Cloud
- **Docker** - Containerization and multi-stage builds
- **AWS Elastic Beanstalk** - Platform-as-a-Service deployment
- **AWS CodePipeline** - CI/CD automation
- **AWS ECR** - Container registry
- **Nginx** - Reverse proxy and load balancing

### Content Management
- **Markdown** - Easy content creation and editing
- **YAML Front Matter** - Metadata management
- **FlexMark** - Markdown to HTML conversion

## 💰 Cost Optimization

This application is designed to run **entirely on AWS Free Tier**:

- ✅ **$0.00 monthly cost** - Uses only free tier resources
- ✅ **EC2 t2.micro** - 750 hours/month free
- ✅ **EBS Storage** - 30 GB free
- ✅ **Load Balancer** - 750 hours/month free
- ✅ **Data Transfer** - 1 GB out/month free

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   AWS Route 53  │────│  AWS CloudFront │────│  AWS ALB        │
│   (DNS)         │    │  (CDN)          │    │  (Load Balancer)│
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                                        │
                       ┌─────────────────┐              │
                       │     Nginx       │◄─────────────┘
                       │  (Reverse Proxy)│
                       └─────────────────┘
                                │
                       ┌─────────────────┐
                       │  Spring Boot    │
                       │  (Java App)     │
                       └─────────────────┘
                                │
                       ┌─────────────────┐
                       │   Markdown      │
                       │   Content       │
                       └─────────────────┘
```

## 🚀 Quick Start

### Local Development

```bash
# Clone the repository
git clone <repository-url>
cd kc-cloud-tutor

# Build with Maven
mvn clean package

# Run with Docker
docker-compose up --build

# Access the application
open http://localhost:8080
```

### AWS Deployment

```bash
# Build and push to ECR
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin <account-id>.dkr.ecr.us-east-1.amazonaws.com
docker build -t kc-cloud-tutor .
docker tag kc-cloud-tutor:latest <account-id>.dkr.ecr.us-east-1.amazonaws.com/kc-cloud-tutor:latest
docker push <account-id>.dkr.ecr.us-east-1.amazonaws.com/kc-cloud-tutor:latest
```

## 📝 Content Management

### Adding New Tutorials

1. Create a new Markdown file in `src/main/resources/content/`
2. Add YAML front matter with metadata
3. Write content in Markdown
4. Deploy - content is automatically loaded

### Example Tutorial File

```markdown
---
title: "Your Tutorial Title"
description: "Brief description"
category: "aws"
difficulty: "beginner"
duration: "30 min"
publishedAt: "2024-01-01"
tags: ["aws", "tutorial"]
author: "KC Cloud Tutor"
featured: true
---

# Your Tutorial Content

Write your tutorial content here using Markdown...

## Features

- **Bold text** for emphasis
- *Italic text* for highlights
- `Code blocks` for examples
- Lists and more!
```

## 🔧 Configuration

### Application Properties

```yaml
server:
  port: 8080

spring:
  thymeleaf:
    cache: true
  resources:
    static-locations: classpath:/static/

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
```

### Docker Configuration

```dockerfile
# Multi-stage build for optimization
FROM maven:3.8.6-openjdk-11-slim AS build
# ... build steps ...

FROM openjdk:11-jre-slim
# ... runtime configuration ...
```

## 📊 Monitoring

### Health Checks

- Application: `http://localhost:8080/actuator/health`
- Docker: `docker ps`
- AWS: CloudWatch metrics

### Cost Monitoring

- AWS Cost Explorer
- Billing alerts configured
- Resource tagging for allocation

## 🛠️ Development

### Prerequisites

- Java 11+
- Maven 3.6+
- Docker 20.0+
- AWS CLI configured

### Build Commands

```bash
# Compile and test
mvn clean compile test

# Package application
mvn clean package

# Run application
mvn spring-boot:run

# Build Docker image
docker build -t kc-cloud-tutor .

# Run with Docker Compose
docker-compose up --build
```

## 🚀 Deployment Pipeline

1. **Code Commit** → Git repository
2. **CodePipeline Trigger** → AWS CodePipeline
3. **Build Phase** → Maven build + Docker build
4. **Deploy Phase** → AWS Elastic Beanstalk
5. **Health Check** → Application health verification

## 📈 Performance

### Optimizations

- **Docker multi-stage build** - 60% smaller images
- **JVM tuning** - Optimized for low memory usage
- **Static content caching** - 1-year cache headers
- **Gzip compression** - Reduced bandwidth usage
- **Nginx reverse proxy** - Load balancing and caching

### Resource Usage

- **Memory**: 128MB - 256MB
- **CPU**: 0.1 - 0.5 cores
- **Storage**: < 100MB application + content

## 🔒 Security

### Security Features

- **HTTPS only** in production
- **Security headers** configured
- **Rate limiting** implemented
- **Input validation** and sanitization
- **No hardcoded credentials**

### Best Practices

- Least privilege IAM roles
- Regular security updates
- Container security scanning
- Log monitoring and alerting

## 📚 Skills Demonstrated

### Java Development
- Object-oriented programming
- Spring Boot framework
- RESTful API design
- Dependency injection
- Configuration management

### DevOps Engineering
- Docker containerization
- Multi-stage builds
- CI/CD pipeline automation
- Infrastructure as Code
- Monitoring and logging

### AWS Cloud Services
- Elastic Beanstalk deployment
- ECR container registry
- CodePipeline automation
- CloudWatch monitoring
- Cost optimization

### Full-Stack Development
- Backend API development
- Frontend template rendering
- Database design (file-based)
- Content management system
- Responsive web design

## 📞 Support

For questions or issues:

1. Check the [Cost Optimization Guide](COST_OPTIMIZATION.md)
2. Review AWS CloudWatch logs
3. Check application health endpoints
4. Monitor resource usage and costs

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

**Built with ❤️ using Java, Spring Boot, Docker, and AWS**

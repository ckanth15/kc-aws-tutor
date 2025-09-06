# KC Cloud Tutor - Cost Optimization Guide

## AWS Free Tier Usage

This application is designed to run entirely on the **AWS Free Tier** to minimize costs:

### ✅ Free Tier Compatible Components

1. **EC2 Instance**: t2.micro (750 hours/month free)
2. **Elastic Beanstalk**: Free tier includes 750 hours of EC2 usage
3. **Application Load Balancer**: Free tier includes 750 hours
4. **EBS Storage**: 30 GB of General Purpose (SSD) storage
5. **Data Transfer**: 1 GB out per month

### 💰 Estimated Monthly Cost: $0.00

## Docker Optimization

### Multi-stage Build
- Reduces final image size by 60%
- Only includes runtime dependencies
- Uses Alpine Linux base image

### Resource Limits
```yaml
# docker-compose.yml
services:
  kc-cloud-tutor:
    deploy:
      resources:
        limits:
          memory: 512M
          cpus: '0.5'
```

## Java Application Optimization

### JVM Settings
```bash
# Optimized for low memory usage
java -Xms128m -Xmx256m -XX:+UseG1GC -jar app.jar
```

### Spring Boot Optimization
- Disabled unnecessary auto-configuration
- Minimal logging configuration
- Static resource caching
- Gzip compression enabled

## Monitoring & Alerts

### Cost Monitoring
- CloudWatch billing alarms set at $5, $10, $25
- Daily cost reports via AWS Cost Explorer
- Resource tagging for cost allocation

### Health Checks
- Application health endpoint: `/actuator/health`
- Docker health checks configured
- Nginx health monitoring

## Scaling Strategy

### Horizontal Scaling
- Auto Scaling Group (1-3 instances)
- Load balancer distribution
- Stateless application design

### Vertical Scaling
- Start with t2.micro
- Scale to t2.small if needed
- Monitor CPU and memory usage

## Backup Strategy

### Data Backup
- EBS snapshots (free tier: 30 GB)
- Application logs to CloudWatch
- Configuration in Git repository

### Disaster Recovery
- Multi-AZ deployment
- Automated backups
- Quick recovery procedures

## Security & Compliance

### Security Groups
- Minimal required ports (80, 443, 8080)
- SSH access restricted to specific IPs
- HTTPS only in production

### IAM Roles
- Least privilege access
- No hardcoded credentials
- Role-based access control

## Performance Optimization

### Caching
- Static content caching (1 year)
- Application-level caching
- CDN integration ready

### Database
- No database required (file-based content)
- Markdown files in Git
- Zero database costs

## Cost Breakdown

| Component | Free Tier | Monthly Cost |
|-----------|-----------|--------------|
| EC2 (t2.micro) | 750 hours | $0.00 |
| EBS Storage | 30 GB | $0.00 |
| Load Balancer | 750 hours | $0.00 |
| Data Transfer | 1 GB out | $0.00 |
| **Total** | | **$0.00** |

## Monitoring Commands

```bash
# Check application health
curl http://localhost:8080/actuator/health

# Check Docker container status
docker ps

# Monitor resource usage
docker stats

# Check AWS costs
aws ce get-cost-and-usage --time-period Start=2024-01-01,End=2024-01-31 --granularity MONTHLY --metrics BlendedCost
```

## Alerts Configuration

### CloudWatch Alarms
1. **High CPU Usage**: > 80% for 5 minutes
2. **High Memory Usage**: > 90% for 5 minutes
3. **Billing Alert**: > $5.00
4. **Application Down**: Health check failed

### Notification Channels
- Email alerts for billing
- SNS topics for application alerts
- Slack integration (optional)

## Optimization Checklist

- [ ] Enable CloudWatch detailed monitoring
- [ ] Set up billing alerts
- [ ] Configure auto-scaling
- [ ] Implement caching
- [ ] Monitor resource usage
- [ ] Regular cost reviews
- [ ] Clean up unused resources
- [ ] Optimize Docker images
- [ ] Use Spot instances for non-critical workloads
- [ ] Implement proper logging levels

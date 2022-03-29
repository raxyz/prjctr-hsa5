### Pre requisits 

- AWS account

## Autoscale group task 

According to the [guide](https://docs.aws.amazon.com/autoscaling/ec2/userguide/as-register-lbs-with-asg.html), 
there should be an AMI which you will use in the next steps. 

Create AMI with simple nginx app (previous homework)

![ami](imgs/ami.png)

Then set up a Target Group 

![tg-targets](imgs/tg-targets.png)

and Load Balancer. Pay attantion that Target Group has to be linked with
Load Balancer.

![lb-listeners](imgs/lb-listeners.png)

Create Launch Template

![lt-details](imgs/lt-details.png)

Create Autoscale Group.

![asg-details](imgs/asg-details.png)

Pay attention to configure Spot instances usage

![asg-spot](imgs/asg-spot.png)

and configure scaling policies

![asg-policies](imgs/asg-policies.png)

## Results

Let's test it via siege. 

`siege -c100 -t60s l25-alb-2115072474.us-east-1.elb.amazonaws.com`

As a result, we can see that system detected unusual traffic

![alarm](imgs/alarm.png)

Autoscale Group created new instance

![instances](imgs/instances.png)

and update its activity history too.

![asg-activity](imgs/asg-activity.png)


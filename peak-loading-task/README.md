## Task

Describe solution that solves peak loadings problem for biggest european 
football website https://goal.com  

---

## Solution

### Types of pages

1. News feed pages -- pages with list of news (seems there is not 
personalisation)
2. Live Scores page -- page which uses short polling approach to get new data
3. Object details page -- page with dateiled information of news/team/player/etc.
4. Thin client mobile apps

### Possible sources of peak loadings

### Regular bots activities

This is one of the most popular sites about football and it's required for 
often updates from different search bots to stay at the top of the score 
in search systems with actual news.

**Solution**
Bots activities shouldn't be a big problem, because we are expecting to have 
a lot of concurrent users. But still it's better to limit bot's access to the 
site on nginx level.
Example: https://www.agix.com.au/nginx-and-rate-limiting-search-bots/

### Push notifications 

The site supports browser push as well as mobile app push notifications. 
This means that during some important football match a lot of users can try 
to get the latest news from the app/site.

**Solution**
We are managing all push notifications. So we can cache the news before sending 
pushes. Set small TTL, for example 2 mins. Maybe use probabilistic cache approach

### Short polling request on Live Score pages

The site uses a short polling approach to get the last data from the backend. 
If many users open the live score page -- the backend will receive a bunch of requests.

**Solution**
Caching with an ability to update cache when admin added any changes to the game.
(Don't evict the cashe)

### DDoS attacks 

**Solution**
In this case, limit each user with a number of requests per second and 
add a captcha in case if there are too many requests from one user. 
Of course, use advice from the previous lecture :)

### Championships

Championships can produce a lot of traffic. 

**Solution**
In the case of local championships, we can scale the site to the nearest 
data center of the particular region. In the case of world championships, 
it's better to follow a schedule and increase capacity based 
on the game statistics (popular game or not). 

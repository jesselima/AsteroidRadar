# Asteroid Radar


## Requirements

> This App uses Nasa API [NASA Api](https://api.nasa.gov/#signUp). So you need to request an API key.
> To be able to play YouTube videos you will also need a YouTube API key.

Set both keys on the ```gradle.properties``` file place holders: 

YOUTUBE_KEY="PUT_YOUR_YOUTUBE_API_KEY_HERE"

API_KEY="PUT_YOUR_NASA_API_KEY_HERE"

## Features

At the first launch if the device is connected, the App will try to retrieve remote data. If not 
connected, the WorkManager will schedule a background task and run when its constraints are met. 
At the first launch, if device is connected, the app gets the latest pictures and asteroids and save 
them to local database. 


**The Asteroid side of the features:**

- Get Asteroids data for the next 7 days. This data persists offline.
- See Asteroids details. This also uses offline feature. 
- Asteroids details provides a link to NASA JPL Web site for more info about the asteroid.
- New data is retrieved once a day on a background task by the WorkManager. When new data is retrieved, old data behind 
today is deleted.  
- Asteroids can be filtered by today or by current week.
- Asteroids can be sorted by distance, by speed and by date.

**The Pictures side of the features:**

- Get Picture of the day for the last 10 days. The data persists offline.
- Pictures are displayed in a format of gallery that you can swipe them around from the main screen. 
- See picture details. 
- Play YouTube vídeos when the media of the day is a video.
- Save pictures to favorites.
- Load high definition image from the Picture details where it can be zoom in or out.
- Zoom picture from picture details screen or from full screen (uses PhotoView https://github.com/Baseflow/PhotoView).
- Pictures can be filtered by favorites only or show all pictures.
- Favorites pictures can be reset at once (all pictures) or one by one at the picture details screen.
- Viewed images remains in the Picasso offline cache, so it can be viewed offline. 
- Picture in high definition can be viewed in full screen with drag and zoom in or out from full screen.
- All favorite pictures can be viewed from a gallery.

# Accessibility

The App can be used with Talkback.


# VIDEO DEMO

https://user-images.githubusercontent.com/17894156/109640100-c8a68380-7b2e-11eb-987a-a335bd9f4333.mp4

Raw:

https://github.com/jesselima/AsteroidRadar/blob/master/screenshots_and_video/video/app-asteroid-radar.mp4


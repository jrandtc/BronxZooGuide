#  Bronx Zoo Guide: An Unofficial Tour

Many museums have cheap digital audio tours where you use an app or device where you enter numbers at stations to listen to more info about what you’re looking at. In NYC the Met (https://www.metmuseum.org/visit/audio-guide) and MoMA have great ones; also in France there are bunch of great ones in the Loire Valley.

The Bronx Zoo doesn’t have an audio tour or anything like it (closest thing is founder William Temple Hornaday’s 1899 Guide to the New York Zoological Park; read here  https://babel.hathitrust.org/cgi/pt?id=uc1.c045667025 ). We’d like our audio guide to include both historical information about the park (which is weirdly hard to find except in primary sources like newspaper archives and Hornaday’s book; possibly because of scandals in its history – they did lots of things that wouldn’t be okay by today’s standards although they were progressive at the time) and information about the animals taken from the Encyclopedia of Life (eol.org) and Wikipedia (their information isn’t always reliable but it’s not copyrighted and worth using as long as you double check)

![Screenshot 2](https://raw.githubusercontent.com/tc10815/BronxZooGuide/master/images/planning/drawing2.jpg) ![Screenshot 1](https://raw.githubusercontent.com/tc10815/BronxZooGuide/master/images/planning/drawing1.jpg)

Screenshot 1                                                  Screenshot 2
## App Flow ##

When the app opens, the user is presented with a scrollable list of nearby animals and structures with arrows pointing towards them, as pictured above in screenshot 1.  If the user taps an animal or empty structure, they go to an information page with detailed information about the animal/structure with audio they can listen to about it, as pictured in screenshot 2.  

If the user taps one of the 7 structures containing animals in the first list (e.g. Mouse House, World of Birds, Reptile World), a second list of all the animals inside that structure will appear in order of where they are found in the building, from entrance to exit. A stub of these animals can be clicked for limited additional information (no custom content, but links and a picture) 

Finally, there will be a little search bar on top of the first list (screenshot 1, not pictured), that will allow users to find specific animals they are looking for, by any criteria hard coded in the XML, such as names, family, class or binomial nomenclature. When text is added to the search box, the list automatically filters to only include animals, structures or structures containing the animals matching the new filter.

## 1.	What is/are the problem/s you want to solve? Please provide background based on research. 

There are currently no audio guides (or modern physical guides) to the Bronx Zoo.  Audio guides make sightseeing much more rewarding. 

## 2.	Who are the users you are targeting? Please use a Persona to describe your users and draw a picture of your target user. 

Persona: Tom Soncook

Tom is a curious young adult who enjoys going to museums with his girlfriend on weekends; a much needed respite from his stressful job. Tom goes to the zoo with his girlfriend one weekend; he's interested in the animals at the Bronx zoo but he finds the educational plaques to be overwhelming in quantity and distracting from the animals. Tom would rather be looking at animals than reading plaques all day. Also, he finds that there's not enough information on many of the plaques.

Thankfully Tom and his date are nerds and have heard of the Unofficial Bronx Zoo Audio Guide. Tom installs the audio guide on his android, and takes 2 bluetooth headsets for him and his date on the trip. Tom has a limit of 2gb per month of 4G, so he installed the app at home before he left.

Once Tom gets to the zoo he spends a day working through about half of the animals until he gets tired and it starts to get late. Tom is curious about the population distribution of American Bison in case he wants to see one in the wild, and is able to quickly look it up using the Encyclopedia of Life link in the app. He also sees that the app saves check marks next to all the animals he has already visited, so he knows he won't have to take notes to keep from repeating himself the next time he visits. 

Tom and his date have a peaceful drive home talking about all the interesting animal facts they have learned.

## 3.	What are the major features of the solution? List the features.
- Lists every animal at the zoo
- Provides an audio tour description of every outside animal and many structures. 
- Includes buildings with animals inside (e.g. Reptile house) that users can click to expand, where users can see animals with pictures and links, but no audio/text guide.
- Points to every animal / building in the zoo so one can find them
- Provides a search feature to find specific animals
- Provides fast links to learn all current academic information on each animal at Encyclopedia of Life and Wikipedia

## 4.	How is this solution unique? How does it relate to existing solutions on the market? What are three solutions similar or related to what you want to do in the Google Play Store? Do the research and know that you are probably not reinventing the wheel! Present these three solutions, a description and how they compare to yours. Provide screenshots of the existing solutions.
There are no existent audio guides for the Bronx Zoo. Naturally, there are countless app audio guides for various musuems and parks. 

Although not a "Audio Tour Guide" app, the Bronx zoo does have a mobile website which gives users some logistical information about the park, and the location of a few animals.

![Screenshot 1](https://raw.githubusercontent.com/tc10815/BronxZooGuide/master/images/planning/bz1.png)
![Screenshot 2](https://raw.githubusercontent.com/tc10815/BronxZooGuide/master/images/planning/bz2.png) 

![Screenshot 1](https://raw.githubusercontent.com/tc10815/BronxZooGuide/master/images/planning/bz3.png)
![Screenshot 2](https://raw.githubusercontent.com/tc10815/BronxZooGuide/master/images/planning/bz4.png)

Although the logistical information the Bronx Zoo mobile website gives is helpful, it has no information about the animals.

The MET and MoMA also have mobile websites instead of mobile apps, but they actually do provide audio tours. The MET does it in an extremely basic way, hosting a playlist off of Soundcloud for each of their exhibits. Users have to follow the tour in order of the playlist, and its quite tricky to navigate.

![The met](https://raw.githubusercontent.com/tc10815/BronxZooGuide/master/images/planning/met1.png)

MoMA gives visiters more flexability than the MET, by using a system where users can enter numbers online to listen to audio information. This makes it so users to can enjoy the exhibits out of order. It also makes the tour easier to find. 

![Screenshot 1](https://raw.githubusercontent.com/tc10815/BronxZooGuide/master/images/planning/moma1.png)
![Screenshot 2](https://raw.githubusercontent.com/tc10815/BronxZooGuide/master/images/planning/moma2.png) 

Rick Steves, a famous tour guide, has a audio tour for Europe which is offered as an android app.  Pictures are below:

![Screenshot 1](https://raw.githubusercontent.com/tc10815/BronxZooGuide/master/images/planning/1.png)
![Screenshot 2](https://raw.githubusercontent.com/tc10815/BronxZooGuide/master/images/planning/0.png)

![Screenshot 3](https://raw.githubusercontent.com/tc10815/BronxZooGuide/master/images/planning/2.png) 

Rick Steves' tours are an Android app and offer downloading the audio to your phone before leaving. This is  hugely helpful for Americans in Europe, who typically won't have 4G on their phones. The content is absolutely top notch, but the implementation of the tour is very basic. There are no interactive maps or GPS features at all, so it's essentially just an MP3 player that allows you to preload playlists with tours. 

Our app would offer a smarter interface than any of these three apps. By using GPS and pointing with a compass, navigation should be easier and less restrictive. Links to the Encyclopedia of Life and Wikipedia also offer a much deeper dive than the other three audio guide solutions.  

## 5.	What technologies, APIs etc will you be using to develop this solution? Provide links and explanations.
- Animal data will be stored locally and included in APK as an XML file
- Images and audio files will be included in APK, although highly compressed
- Google Analytics
- Locations and electromagetic sensors
- Sound Playing API...
- Intents will be used to launch web browsers for sources and email if users want to submit information

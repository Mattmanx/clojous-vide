# clojous-vide
## What? 
An all-in-one application to run my (your?) Raspberry Pi-based Sous Vide cooker. A PID controller to maintain temperature, REST services to control state, target temperature, sensors, etc. and a pretty front-end to consume said services. From a hardware standpoint, I was able to find some outstanding resources online to get to a point where I can read temp from a [DS18B20 "1-Wire" Temperature Sensor](https://learn.adafruit.com/adafruits-raspberry-pi-lesson-11-ds18b20-temperature-sensing/hardware) and [control an RF power outlet](http://timleland.com/wireless-power-outlets/). As a bonus, I had an excuse to buy and play with a soldering iron, so I've already seen a considerable return on the time commitment thus far.  I know that's not much to go off of - I'll put together an .io page with some more details on how to turn your $35 Pi into a mean water bath cooking machine once I've got the software in a good state. 

## Why?
Yeah, I know [Anova](http://anovaculinary.com/anova-precision-cooker/) already makes a great Sous Vide. It has a nice mobile app, it has bluetooth/wifi, and it's pretty. But cooking delicious meats is only a secondary goal of this project. I have *at least* a few other neglected items on my dusty TODO list of personal projects, coincidentally sitting right next to that line item to use the Raspberry Pi that I got for Christmas last year:
* Lots of neglected TODO's
* **Learn Clojure**
* **Learn ReactJS**
* **Use Raspberry Pi**
* Lots of neglected TODO's

So, I thought, why not kill 2 (3?) birds with one stone and incorporate several into a single project? 

## How? 
This is a work in progress, but here's the plan: 
* Clojure Components
 * **PID Controller** - See [Wikipedia](https://en.wikipedia.org/wiki/PID_controller) 
 * **SQLite Adapter** - Persist our sensor readings, target temperature, and other stateful things. 
 * **REST Services** - Expose access to the SQLite side effects via HTTP.
* ReactJS Components
 *  A **front-end** to consume the REST services.

# Getting Started
TBD



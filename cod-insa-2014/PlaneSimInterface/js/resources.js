//Cod INSA 2014 final round
//Settings for the web interface
//Nicolas Vailliet
//April 2014

//Images
/*
var base_icon = new Array(
  {
    path: 'm0,-20-17.71875,16.28125h5.5625v15.53125h24.3125v-15.53125h5.5625l-17.71875-16.28125z',
    fillColor: 'white',
    fillOpacity: 1.0,
    scale: 1,
    rotation:0
  },
   {
    path: 'm0,-20-17.71875,16.28125h5.5625v15.53125h24.3125v-15.53125h5.5625l-17.71875-16.28125z',
    fillColor: 'red',
    fillOpacity: 1.0,
    scale: 1,
    rotation:0
  },
  {
    path: 'm0,-20-17.71875,16.28125h5.5625v15.53125h24.3125v-15.53125h5.5625l-17.71875-16.28125z',
    fillColor: 'green',
    fillOpacity: 1.0,
    scale: 1,
    rotation:0
  },
  {
    path: 'm0,0-17.71875,16.28125h5.5625v15.53125h24.3125v-15.53125h5.5625l-17.71875-16.28125z',
    fillColor: 'blue',
    fillOpacity: 1.0,
    scale: 1,
    rotation:0
  },
  {
    path: 'm0,0-17.71875,16.28125h5.5625v15.53125h24.3125v-15.53125h5.5625l-17.71875-16.28125z',
    fillColor: 'yellow',
    fillOpacity: 1.0,
    scale: 1,
    rotation:0
  },
  {
    path: 'm0,0-17.71875,16.28125h5.5625v15.53125h24.3125v-15.53125h5.5625l-17.71875-16.28125z',
    fillColor: 'pink',
    fillOpacity: 1.0,
    scale: 1,
    rotation:0
  },
  {
    path: 'm0,0-17.71875,16.28125h5.5625v15.53125h24.3125v-15.53125h5.5625l-17.71875-16.28125z',
    fillColor: 'black',
    fillOpacity: 1.0,
    scale: 1,
    rotation:0
  });*/


var base_icon = new Array(
	"../img/home/home_white.png", 
	"../img/home/home_red.png", 
	"../img/home/home_green.png", 
	"../img/home/home_blue.png", 
	"../img/home/home_yellow.png", 
	"../img/home/home_pink.png", 
	"../img/home/home_black.png");
var fighter_icon = new Array(
	"../img/army/air_force_white.png",
	"../img/army/air_force_red.png", 
	"../img/army/air_force_green.png", 
	"../img/army/air_force_blue.png", 	
	"../img/army/air_force_yellow.png", 
	"../img/army/air_force_pink.png",
	"../img/army/air_force_black.png");
var civilian_icon = new Array(
	"../img/people/plane_white.png",
	"../img/people/plane_red.png", 
	"../img/people/plane_green.png", 
	"../img/people/plane_blue.png", 	
	"../img/people/plane_yellow.png", 
	"../img/people/plane_pink.png", 
	"../img/people/plane_black.png");

Array.prototype.remove = function() {
    var what, a = arguments, L = a.length, ax;
    while (L && this.length) {
        what = a[--L];
        while ((ax = this.indexOf(what)) !== -1) {
            this.splice(ax, 1);
        }
    }
    return this;
};

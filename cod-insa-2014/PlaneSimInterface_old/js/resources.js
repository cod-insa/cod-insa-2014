//Cod INSA 2014 final round
//Settings for the web interface
//Nicolas Vailliet
//April 2014

//Images
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

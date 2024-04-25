let k
navigator.geolocation.getCurrentPosition(function(pos) {
    var latitude = pos.coords.latitude;
    var longitude = pos.coords.longitude;
    alert("현재 위치는 : " + latitude + ", "+ longitude);
    k = {latitude:latitude+"", longtitude:longitude+""};
});
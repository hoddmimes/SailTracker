
    mapboxgl.accessToken = mapbox_key;
    var zeroMarker;
    var zeroMarkerMessage;
    var geoJsonData;

    function loadMap( pGeoJsonData, zoomLevel ) {
     geoJsonData = pGeoJsonData
     zeroMarker = geoJsonData.features[0].geometry.coordinates;
     zeroMarkerMessage = geoJsonData.features[0].properties.message;


         map = new mapboxgl.Map({
                   container: 'map', // container id
                   style: 'mapbox://styles/mapbox/streets-v11', // style URL
                   center: zeroMarker, // starting position [lng, lat]
                   zoom: zoomLevel }); // starting zoom

         map.loadImage('/sailtracker/images/pin32.png', function(error, image) {
           if (error) throw error;
           map.addImage('pin32', image);
         });

         map.loadImage('/sailtracker/images/redicon.png', function(error, image) {
            if (error) throw error;
            map.addImage('redicon', image);
         });

         map.on('load',mapData);

      	 const el = document.createElement('div');
      	 el.className = 'marker';

      	 // make a marker for each feature and add to the map
      	 new mapboxgl.Marker(el).setLngLat(zeroMarker).addTo(map);
      	 return;
    }  // loadMapData


    function mapData() {

       map.addSource('sailtracker', {
            type: 'geojson',
            data: geoJsonData,
            cluster: true,
            clusterMaxZoom: 9, // Max zoom to cluster points on
            clusterRadius: 50 // Radius of each cluster when clustering points (defaults to 50)
         });

      map.addLayer({
         id: 'clusters',
         type: 'circle',
         source: 'sailtracker',
         filter: ['has', 'point_count'],
         paint: {
            // Use step expressions (https://docs.mapbox.com/mapbox-gl-js/style-spec/#expressions-step)
            // with three steps to implement three types of circles:
            //   * Blue, 20px circles when point count is less than 100
            //   * Yellow, 30px circles when point count is between 100 and 750
            //   * Pink, 40px circles when point count is greater than or equal to 750
            'circle-color': [
                  'step',
                  ['get', 'point_count'],
                  '#51bbd6',
                  100,
                  '#f1f075',
                  750,
                  '#f28cb1'
            ],
            'circle-radius': [
               'step',
               ['get', 'point_count'],
               20,
               100,
               30,
               750,
               40
            ]
         }
      });

      map.addLayer({
         id: 'cluster-count',
         type: 'symbol',
         source: 'sailtracker',
         layout: {
            'text-field': '{point_count_abbreviated}',
            'text-font': ['DIN Offc Pro Medium', 'Arial Unicode MS Bold'],
            'text-size': 12
         }
      });




      map.addLayer({
         id: 'unclustered-point',
         type: 'symbol',
         source: 'sailtracker',
         filter: ['!', ['has', 'point_count']],
         layout: {
               'icon-image': 'pin32',
               'icon-size': 1,
               'icon-anchor': 'bottom',
               'icon-allow-overlap': true
         }
       });




     if (geoJsonData.features.length > 1) {

         map.addSource('point', {
	        'type': 'geojson',
	        'data': {
	            'type': 'FeatureCollection',
	            'features': [
		            {
		                'type': 'Feature',
		                'geometry': {
			                'type': 'Point',
			                'coordinates':  zeroMarker
		                }
		            }
	            ]
	        }
        });

        map.addLayer({
            'id': 'point',
            'type': 'symbol',
            'source': 'point',
            'layout': {
	            'icon-image':'redicon',
		        'icon-size': 1.0,
		        'icon-anchor': 'bottom',
                'icon-allow-overlap': true
	        }
          });
      }


       map.on('click', 'unclustered-point', function (e) {
            new mapboxgl.Popup()
               .setLngLat(e.features[0].geometry.coordinates)
               .setHTML(e.features[0].properties.message)
               .addTo(map);
       });


       map.on('click', 'point', function (e) {
            new mapboxgl.Popup()
               .setLngLat(e.features[0].geometry.coordinates)
               .setHTML('latest known position ' + zeroMarkerMessage)
               .addTo(map);
       });

     return;
	}
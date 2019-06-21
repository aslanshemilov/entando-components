const OPTIONS = {
  url:
    "https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw",
  copyright: `Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, '
      Imagery © <a href="https://www.mapbox.com/">Mapbox</a>`
};

const PATH_IMG = "../resources/plugins/dashboard/static/img/";

const panelToolTip =(info) =>(`
  <div class="panel panel-info">
    <div class="panel-heading">
      <div>
        <i class="fa fa-product-hunt fa-2x" aria-hidden="true"></i>
        <h2 class="title-parking">
          <div>Parcheggio</div>
          <div><small>P.zza Matteoti</small></div>
        </h2>
      </div>
    </div>
    <div class="panel-body">
      <ul class="list-group">
        <li class="list-group-item">
          <div class="li-point-libero"></div>
          <div class="parking-liberi">Liberi: ${info.liberi}</div>
        </li>
        <li class="list-group-item">
          <div class="li-point-occupati"></div>
          <div class="parking-occupati">Occupati: ${info.occupati} </div>
        </li>
        <li class="list-group-item">
          <div class="li-point-totale"></div>
          <div class="parking-totale">Totali: ${info.totale}</div>
        </li>
      </ul>
    </div>
  </div>
  `);



class Map {
  constructor(
    id,
    latitude = 39.223841,
    longitude = 9.121661,
    zoom = 13,
    position
  ) {
    const streetsLayer = L.tileLayer(OPTIONS.url, {
      id: "mapbox.streets",
      attribution: OPTIONS.copyright
    });
    const satelliteLayer = L.tileLayer(OPTIONS.url, {
      id: "mapbox.satellite",
      attribution: OPTIONS.copyright
    });

    this.map = L.map(id, {
      center: [latitude, longitude],
      zoom: zoom,
      minZoom: 10,
      maxZoom: 18,
      layers: [streetsLayer, satelliteLayer],
      zoomControl: false
    });
    this.baseMaps = {
      Satellite: satelliteLayer,
      Streets: streetsLayer
    };

    this.controlZoom = new L.control.zoom({position}).addTo(this.map);
    this.control = new L.control.layers(this.baseMaps, null, {
      position
    }).addTo(this.map);
    this.layerGroup = new L.layerGroup();
  }

  getIcon(item, icon) {
    const test = (check, val) => {
      const str = `${check}`.replace(new RegExp("val", "g"), val);
      return new Function(
        `if ( ${str} ) { return true; }  else return false;`
      ).call(val);
    };

    let className = "default fa fa-map-marker";
    if (icon) {
      const {rules} = icon;
      if (icon.rules) {
        const {key, conditions} = rules;
        for (let condition of conditions) {
          className = test(condition.check, item[key])
            ? `${condition.result} ${icon.marker}`
            : className;
        }
      }
    } else {
      className = className.concact("fa fa-map-marker");
    }
    return L.divIcon({className: `marker-icon-${className} `});
  }

  addLayer(layer, options) {
    this.control.removeLayer(this.layerGroup);
    console.log('layer', layer);
    const {title, data} = layer;
    const icon = layer.icon;
    data.forEach(item => {
      const lat = _.get(item, options.latitude);
      const lon = _.get(item, options.longitude);
      const info = _.get(item, options.information);
      console.log('info', info);
      if (lat !== null && lon !== null) {
        L.marker([lat, lon], {
          icon: this.getIcon(item, icon)
        })
          .bindPopup(panelToolTip(info), {

          })
          .addTo(this.layerGroup);
      }
    });

    this.control.addOverlay(this.layerGroup, [title]);
  }

  showData(datasources) {

    loadData(
      // inserire url endpoint `${context}api/plugins/dashboard/server/${serverName}/datasource/${datasource}/data`
    ).then(paking => {
      this.addLayer(datasources[0], {
        latitude: "DeviceLocations.latitude",
        longitude: "DeviceLocations.longitude",
        information: "DeviceInformation"
      });
    });
  }
}

$(document).ready(() => {
  // main
  console.log("eseguo la mappa");
  const map = new Map("map", 39.223841, 9.121661, 13, "topright");
  map.showData(CONFIG_MAP.datasources);
   // setInterval(() => {
   //   map.showData(CONFIG_MAP.datasources);
   // }, 10000);
});

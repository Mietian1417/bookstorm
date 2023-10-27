//****************所使用的数据是city.js******************//

/*根据id获取对象*/
function $(str) {
    return document.getElementById(str);
}

var addrShow = $('addr-show');
var addrpro = $('addr-pro');
var addrcity = $('addr-city');
var addrdis = $('addr-dis');

//var btn = document.getElementsByClassName('met1')[0];
var prov = $('prov');
var city = $('city');
var country = $('country');


/*用于保存当前所选的省市区*/
var current = {
    prov: '',
    city: '',
    country: ''
};

// 加载省份列表
// 现在有一个问题, 当点击 select 标签时, 会调用这个函数,
// 但是当选中 option 时, 这个函数也会被调用, 所以有个 flag 来禁止第二次调用
let flagProvince = true;
function showProv() {
    if (flagProvince) {
        while (prov.firstChild) {
            prov.removeChild(prov.firstChild);
        }
    }
    flagProvince = !flagProvince;

    for (let i = 0; i < provice.length; i++) {
        var provOpt = document.createElement('option');
        provOpt.innerText = provice[i]['name'];
        provOpt.value = i;
        prov.appendChild(provOpt);
        // console.log(provOpt.innerText);  // 值
        // console.log(provOpt.value);  // 序号
    }
    // 重新选择省, 清空城市和县区
    city.value = '';
    country.value = '';
}

// 根据所选的省份来显示城市列表
// 现在有一个问题, 当点击 select 标签时, 会调用这个函数,
// 但是当选中 option 时, 这个函数也会被调用, 所以有个 flag 来禁止第二次调用
let flagCity = true
function showCity() {
    // var val = obj.options[obj.selectedIndex].value;
    let val = document.querySelector('#prov').value;
    if (flagCity) {
        while (city.firstChild) {
            city.removeChild(city.firstChild);
        }
    }
    flagCity = !flagCity;

    // 重新选择城市
    if (val != current.prov) {
        current.prov = val;
        city.length = 0;
    }

    if (val != null) {
        // 修改信息时, 直接修改城市, 这个时候的 val 是具体的省份名称, 要修改成序号
        if(typeof val == 'string') {
            for (let i = 0; i < provice.length; i++) {
                if (provice[i].name === val){
                    val = i;
                    break;
                }
            }
        }
        var cityLen = provice[val]["city"].length;
        for (var j = 0; j < cityLen; j++) {
            var cityOpt = document.createElement('option');
            cityOpt.innerText = provice[val]["city"][j].name;
            cityOpt.value = j;
            city.appendChild(cityOpt);
        }
    }
    // 重新选择城市, 清空县区
    country.value = '';

}

// 根据所选的城市来显示县区列表
// 现在有一个问题, 当点击 select 标签时, 会调用这个函数,
// 但是当选中 option 时, 这个函数也会被调用, 所以有个 flag 来禁止第二次调用
let flagCountry = true;
function showCountry() {
    // var val = obj.options[obj.selectedIndex].value;
    let val = document.querySelector('#city').value;
    if (flagCountry) {
        while (country.firstChild) {
            country.removeChild(country.firstChild);
        }
    }
    flagCountry = !flagCountry;
    if (current.city != val) {
        current.city = val;
        country.length = 0;
    }

    if (val != null) {
        // 修改信息时, 直接修改县区, 这个时候的 val 是具体的城市名称, 要修改成序号
        if(typeof val == 'string') {
            for(let i = 0; i < provice.length; i++) {
                let curCities = provice[i]["city"];
                for(let j = 0; j < curCities.length; j++){
                    if(curCities[j]["name"] == val){
                        current.prov = i;
                        val = j;
                    }
                }
            }
        }
        // 修改信息时, 直接修改城市, 再修改县区, 这个时候的 current.prov 是具体的省份名称, 要转回序号
        if(typeof current.prov == 'string'){
            for(let i = 0; i < provice.length; i++){
                if(provice[i]["name"] == current.prov){
                    current.prov = i;
                }
            }
        }
        var countryLen = provice[current.prov]["city"][val].districtAndCounty.length;
        if (countryLen == 0) {
            addrShow.value = provice[current.prov].name + '-' + provice[current.prov]["city"][current.city].name;
            return;
        }
        for (var n = 0; n < countryLen; n++) {
            var countryOpt = document.createElement('option');
            countryOpt.innerText = provice[current.prov]["city"][val].districtAndCounty[n];
            countryOpt.value = n;
            country.appendChild(countryOpt);
        }
    }
}

/* 填写完第三个选择框, 失去焦点的时候, 将值赋回 */
function showAddr() {
    addrpro.value = provice[current.prov].name;
    addrcity.value = provice[current.prov]["city"][current.city].name;
    // 获取选择的值
    let val = document.querySelector('#country').value;
    // 得到所选城市的所有县区
    let districts = provice[current.prov]["city"][current.city].districtAndCounty;
    // 遍历县区序号, 将县区名称赋上
    for (let i = 0; i < districts.length; i++) {
        if (i == val) {
            addrdis.value = districts[i];
            break;
        }
    }
}

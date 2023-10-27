//****************��ʹ�õ�������city.js******************//

/*����id��ȡ����*/
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


/*���ڱ��浱ǰ��ѡ��ʡ����*/
var current = {
    prov: '',
    city: '',
    country: ''
};

// ����ʡ���б�
// ������һ������, ����� select ��ǩʱ, ������������,
// ���ǵ�ѡ�� option ʱ, �������Ҳ�ᱻ����, �����и� flag ����ֹ�ڶ��ε���
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
        // console.log(provOpt.innerText);  // ֵ
        // console.log(provOpt.value);  // ���
    }
    // ����ѡ��ʡ, ��ճ��к�����
    city.value = '';
    country.value = '';
}

// ������ѡ��ʡ������ʾ�����б�
// ������һ������, ����� select ��ǩʱ, ������������,
// ���ǵ�ѡ�� option ʱ, �������Ҳ�ᱻ����, �����и� flag ����ֹ�ڶ��ε���
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

    // ����ѡ�����
    if (val != current.prov) {
        current.prov = val;
        city.length = 0;
    }

    if (val != null) {
        // �޸���Ϣʱ, ֱ���޸ĳ���, ���ʱ��� val �Ǿ����ʡ������, Ҫ�޸ĳ����
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
    // ����ѡ�����, �������
    country.value = '';

}

// ������ѡ�ĳ�������ʾ�����б�
// ������һ������, ����� select ��ǩʱ, ������������,
// ���ǵ�ѡ�� option ʱ, �������Ҳ�ᱻ����, �����и� flag ����ֹ�ڶ��ε���
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
        // �޸���Ϣʱ, ֱ���޸�����, ���ʱ��� val �Ǿ���ĳ�������, Ҫ�޸ĳ����
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
        // �޸���Ϣʱ, ֱ���޸ĳ���, ���޸�����, ���ʱ��� current.prov �Ǿ����ʡ������, Ҫת�����
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

/* ��д�������ѡ���, ʧȥ�����ʱ��, ��ֵ���� */
function showAddr() {
    addrpro.value = provice[current.prov].name;
    addrcity.value = provice[current.prov]["city"][current.city].name;
    // ��ȡѡ���ֵ
    let val = document.querySelector('#country').value;
    // �õ���ѡ���е���������
    let districts = provice[current.prov]["city"][current.city].districtAndCounty;
    // �����������, ���������Ƹ���
    for (let i = 0; i < districts.length; i++) {
        if (i == val) {
            addrdis.value = districts[i];
            break;
        }
    }
}

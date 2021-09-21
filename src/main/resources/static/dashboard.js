$('#setFreq').click(function (event) {
    $.get(
        'setFreq', {
            freq: 20
        }, function (data) {
            alert(data);
        }
    );
});

$('#getFreq').click(function (event) {
    $.get(
        'getFreq', {}, function (data) {
            alert(data);
        }
    );
});


$('#setMorse').click(function (event) {
    $.get(
        'setMorse', {
            txt: 'test'
        }, function (data) {
            alert(data);
        }
    );
});

$('#getMorse').click(function (event) {
    $.get(
        'getMorse', {}, function (data) {
            alert(data);
        }
    );
});

$('#testMorse').click(function (event) {
    $.get(
        'testMorse', {
            txt: $('#text2morse').val()
        }, function (data) {
            alert(data);
        }
    );
});


function useAudioIn(val) {
    console.log(val);
    $.get(
        'useAudioIn', {
            audio: val
        }, function () {
        }
    );
}

function useAudioOut(val) {
    console.log(val);
    $.get(
        'useAudioOut', {
            audio: val
        }, function () {
        }
    );
}


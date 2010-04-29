var jsfcrud = {};
jsfcrud.busyImagePath = '/ConsultingAgency/faces/busy.gif';
jsfcrud.isDynamicFacesAvailable = typeof DynaFaces != 'undefined';
jsfcrud.canAjaxEnableForm = true;
if (!jsfcrud.isDynamicFacesAvailable) {
    jsfcrud.canAjaxEnableForm = false;
}
if (jsfcrud.isDynamicFacesAvailable) {
    Form.Element.Serializers.selectOne = function(element) {
        var value = '', opt, index = element.selectedIndex;
        if (index >= 0) {
            opt = element.options[index];
            value = opt.value;
        }
        return [element.name, value];
    };
}
jsfcrud.postReplace = function(element, markup) {
    markup.evalScripts();
    setTimeout(function(){jsfcrud.ajaxEnableForm({options: {postReplace:jsfcrud.postReplace}});}, 20);
}
jsfcrud.ajaxEnableForm = function(args) {
    if (!jsfcrud.canAjaxEnableForm) {
        return;
    }
    
    if (typeof args == undefined || args == null) {
        args = {};
    }
    
    if (typeof args.options == 'undefined') {
        args.options = {};
    }
    
    var sourceElement = null;
    if (typeof args.sourceElementId != 'undefined' && args.sourceElementId != null) {
        sourceElement = document.getElementById(args.sourceElementId);
    }
    
    if (typeof args.formId == 'undefined' || args.formId == null) {
        args.formId = 0;
    }
    
    //insert busy image we'll display when sending an Ajax request
    jsfcrud.insertBusyImage();
    
    document.forms[args.formId].submit = function() {
        var busyImage = document.getElementById('busyImage');
        if (busyImage) {
            busyImage.style.display = 'block';
        }
        DynaFaces.fireAjaxTransaction(sourceElement, args.options);
    };
};

jsfcrud.insertBusyImage = function() {
    var busyImage = document.createElement('img');
    busyImage.id = 'busyImage';
    busyImage.src = jsfcrud.busyImagePath;
    busyImage.style.display = 'none';
    document.body.insertBefore(busyImage, document.forms[0]);
}

var AjaxUtil = {};

AjaxUtil.getAllInputAcceptingChildIdArray = function(parentIdArray) {
    var map = {};
    for (var i = 0; i < parentIdArray.length; i++) {
        var element = document.getElementById(parentIdArray[i]);
        if (element == null) {
            continue;
        }
        AjaxUtil.putInputAcceptingIdsRecursively(element, map);
    }
    var allInputAcceptingChildIdArray = [];
    for (var key in map) {
        allInputAcceptingChildIdArray.push(key);
    }
    return allInputAcceptingChildIdArray;
}

AjaxUtil.putInputAcceptingIdsRecursively = function(element, map) {
    var isInputAccepting = false;
    var nodeName = element.nodeName;
    if (nodeName != null) {
        nodeName = nodeName.toLowerCase();
        if (nodeName.indexOf('input') == 0) {
            isInputAccepting = true; //includes buttons
        }
        else if (nodeName.indexOf('textarea') == 0) {
            isInputAccepting = true;
        }
        else if (nodeName.indexOf('select') == 0) {
            isInputAccepting = true;
        }
    }
    if (isInputAccepting == true) {
        map[element.id] = null;
    }
    if (element.hasChildNodes()) {
        for (var i = 0; i < element.childNodes.length; i++) {
            AjaxUtil.putInputAcceptingIdsRecursively(element.childNodes[i], map);
        }
    }
}

AjaxUtil.fireValidationAjaxTransaction = function(sourceElement) {
    if (!jsfcrud.isDynamicFacesAvailable) {
        return;
    }
    var formId = document.forms[0].id;
    var ajaxValidationHiddenId = formId + ':ajaxValidationHidden';
    var ajaxValidationHidden = document.getElementById(ajaxValidationHiddenId);
    if (ajaxValidationHidden) {
        ajaxValidationHidden.value = 'value' + new Date();
    }
    var inputIdArray = AjaxUtil.getAllInputAcceptingChildIdArray([formId]);
    var inputsAsString = inputIdArray.join();
    DynaFaces.fireAjaxTransaction(sourceElement, {inputs:inputsAsString,execute:inputsAsString,render:'messagePanel,' + ajaxValidationHiddenId});
}

setTimeout(function(){jsfcrud.ajaxEnableForm({options: {postReplace:jsfcrud.postReplace}});}, 20);

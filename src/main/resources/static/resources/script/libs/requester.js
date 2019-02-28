var ajaxRequester = (function () {
	var makeRequest = function (method, url, data, success, error) {
            $.ajax({
                    url: url,
                    type: method,
                    data: data || undefined,
                    /*dataType:'json',*/
                    success: success,
                    error: error
            });
	};

	function makeGetRequest (url, data, success, error) {
		return makeRequest('GET', url, data, success, error);
	};

	function makePostRequest (url, data, success, error) {
            return makeRequest('POST', url, data, success, error);
	};

	function makePutRequest (url, data, success, error) {
		return makeRequest('PUT', url, data, success, error);
	};

	function makeDeleteRequest (url, success, error) {
		return makeRequest('DELETE', url, null, success, error);
	};

	return {
            get: makeGetRequest,
            post: makePostRequest,
            put: makePutRequest,
            del: makeDeleteRequest
	};
}());
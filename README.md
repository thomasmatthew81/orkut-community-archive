orkut-community-archive
=======================


Known issues
1. Throughout the code, the pagination logic has not been implemented. The current implementation is to fetch a max of 100 results for any API call.
    This would fail, if any API call would return more than 100 results. To get all results, pagination logic must be implemented
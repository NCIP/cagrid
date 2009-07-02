var resultEvent = new YAHOO.util.CustomEvent({type:"resultEvent",signature:"YAHOO.util.CustomEvent.FLAT"});
var treeEvent = new YAHOO.util.CustomEvent({type:"treeEvent",signature:"YAHOO.util.CustomEvent.FLAT"});
var sortEvent = new YAHOO.util.CustomEvent({type:"sortEvent",signature:"YAHOO.util.CustomEvent.FLAT"});



<!--extend array to find by object.initLabel property-->
    Array.prototype.findByLabel =
    function(labelValue) {
        for (var i = 0; i < this.length; i++) {
            if (this[i].initLabel == labelValue) {
                return this[i];
            }
        }
        return null;
    };

  Array.prototype.clear=function()
  {
      this.length = 0;
  };


   Array.prototype.find = function(searchStr) {
        var idx=-1;
        for (i = 0; i < this.length; i++) {
                if (this[i].match(searchStr)) {
                   idx=i;
                    break;
                }
        }
        return idx;
    };

    <!--class represents a SOLR query. Can push and search parameters -->
    var solrQuery = Class.create({
        initialize: function(searchTerm,rows) {
        YAHOO.log("In constructor of SOLR Query");
        this.start=0;
        this.rows=10;
        if(rows)
           this.rows=rows;

            this.params = [
                'wt=json'
                , 'indent=on'
                , 'hl=true'
                , 'hl.fl=name,features'
            ];
            this.params[this.params.length] = "rows=" + this.rows;
            this.params[this.params.length] = "start=" + this.start;
            this.params[this.params.length] = "q=" + searchTerm;
            this.params[this.params.length] = "tree=on";
        },
        addParam: function(param) {
            this.params.push(param);
        },

        removeParam: function(param) {
            var index = this.params.find(param);
            if(index>-1){
                this.params.splice(index,1);
            }
        },

        addFacet: function(arg, value) {
            this.removeParam("\^fq=" + arg);
            this.addParam("fq=" + arg + ":" + value);
        },
        nextPage: function(){
            this.removeParam("start="+this.start);
            this.start=this.start+this.rows;
            this.addParam("start="+ this.start);
            YAHOO.log("Moved to next page");
        },

        setRows: function(rows){
            this.removeParam("rows="+this.rows);
            this.rows=rows;
            this.addParam("rows="+ this.rows);
        },
         setStartValue: function(startValue){
            this.removeParam("start="+this.start);
            this.start=startValue;
            this.addParam("start="+ this.start);
            YAHOO.log("Moved to specified start value " + startValue);
        },


        getQuery: function() {
            return this.params.join('&');
        },

        sort: function(sortParam){
            this.removeParam("sort="+this.sortParam);
            this.sortParam=sortParam;
            this.addParam("sort="+ this.sortParam);
        }

    });

   








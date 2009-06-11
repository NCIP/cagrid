

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
        }
    });

    <!--represents a catalog item-->
    var SearchTreeNode = Class.create({
        initialize: function(initLabel) {
            this.initLabel = initLabel;
            this.type=initLabel;
            this.count = 1;
            this.updateLabel();
        },
        updateLabel: function() {
            this.label = this.initLabel + " (" + this.count + ")";
        },
        addCount: function() {
            this.count++;
            this.updateLabel();
        }
    });
    <!--represents search results. Will build a search tree as results are added-->
    var SearchResults = Class.create({
        initialize: function() {
            this.searchTree = new Array();
        },
        add: function(result) {
            var typeLabel = result.catalog_type;
            var categoryNode = this.searchTree.findByLabel(typeLabel);

            if (null == categoryNode) {
                this.searchTree.push(new SearchTreeNode(typeLabel));
            }
            <!--else update the count-->
            else {
                categoryNode.addCount();
            }

        },
        <!--will add tree nodes to the supplied tree.-->
        addNodesToTree: function(rootNode) {
            for (var i = 0; i < this.searchTree.length; i++) {
                new YAHOO.widget.TextNode(this.searchTree[i], rootNode);
            }
        }
    });








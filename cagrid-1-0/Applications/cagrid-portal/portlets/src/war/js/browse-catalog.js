

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

    <!--class represents a SOLR query. Can push and search parameters -->
    var solrQuery = Class.create({
        initialize: function(searchTerm) {
            this.params = [
                'wt=json'
                , 'indent=on'
                , 'hl=true'
                ,'rows=1000'
                , 'hl.fl=name,features'
            ];
            this.params[this.params.length] = "q=" + searchTerm;
        },
        addParam: function(param) {
            this.params.push(param);
        },
        removeParam: function(param) {
            this.params.pop(param);

        },
        addFacet: function(arg, value) {
            this.addParam("fq=" + arg + ":" + value);
        },
        getQuery: function() {
            return this.params.join('&');
        }
    });

    <!--represents a catalog item-->
    var SearchTreeNode = Class.create({
        initialize: function(initLabel) {
            YAHOO.log("adding item with label" + initLabel);
            this.initLabel = initLabel;
            this.count = 1;
            this.updateLabel();
        },
        updateLabel: function() {
            this.label = this.initLabel + " (" + this.count + ")";
        },
        addCount: function() {
            YAHOO.log("Count is" + this.count);
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

            YAHOO.log("Searching for node returned " + categoryNode);
            if (null == categoryNode) {
                YAHOO.log("Node is null. Will create node for " + typeLabel);
                this.searchTree.push(new SearchTreeNode(typeLabel));
            }
            <!--else update the count-->
            else {
                YAHOO.log(categoryNode.label);
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


    var tree;
    var categoryNode;
    function initTree() {
        tree = new YAHOO.widget.TreeView("CatalogTree");
        var root = tree.getRoot();
        categoryNode = new YAHOO.widget.TextNode({label:"Category"}, root, true);
        tree.render();
    }

    <!--will show the search menu tree given a properly initialized SearchResults object -->
    function showSearchTree(results) {
        YAHOO.log("Updating tree", "debug");
        tree.removeChildren(categoryNode);
        results.addNodesToTree(categoryNode);
        categoryNode.refresh();
        categoryNode.expand();

    }

  
<style>

    #mainPanel${instance.id}{
        border: 1px solid black;
        height: 200px;
        width: 70%;
        background-color: #fff;
    }

     div.data {
        overflow: auto;
        height: 100%;
        width: 100%;
    }


</style>

    <div id="mainPanel${instance.id}">
        <div class="data">
            ${instance.error}
        </div>
    </div>

<script type="text/javascript">
    new YAHOO.util.Resize('mainPanel${instance.id}');
</script>
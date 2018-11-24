EntityModule.registerInitializer( function ( node ) {

    if ( !$( '#modalHolder' ).length ) {
        $( 'body' ).append(
                $( '<div id="modalHolder" style="z-index: 10000" class="modal fade">' +
                           '<div class="modal-dialog" role="document">' +
                           '<div class="modal-content">' +
                           '<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button><h4 class="modal-title">My popup</h4></div>' +
                           '<div class="modal-body" style="padding-top: 10px;padding-bottom:10px;"></div>' +
                           '</div></div></div>' ) );
    }

    $( '[data-popup]', node ).on( 'click', function ( e ) {
        e.preventDefault();

        var url = $( this ).attr( 'href' );
        var modal = $( '#modalHolder' );

        var applyFragmentHtml = function ( modalBody ) {
            BootstrapUiModule.initializeFormElements( modalBody );

            modalBody.find( '#btn-cancel' ).on( 'click', function ( evt ) {
                modal.modal( 'hide' );
                evt.preventDefault();
            } );

            modalBody.find( '#btn-save' ).on( 'click', function ( evt ) {
                $.ajax( {
                            type: 'POST',
                            url: url,
                            data: modalBody.find( 'form' ).serialize(),
                            success: function ( html ) {
                                modalBody.html( html );
                                applyFragmentHtml( modalBody );
                            }
                        } );
                evt.preventDefault();
            } );
        };
        $( '.modal-body', modal )
                .html( 'Loading...' )
                .load( url, function () {
                    applyFragmentHtml( $( this ) );
                } );

        modal.modal();
    } );
} );
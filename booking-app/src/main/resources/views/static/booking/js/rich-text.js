EntityModule.registerInitializer( function ( node ) {
    var target = node || document;
    ClassicEditor.create( target.querySelector( '[rich-text]' ) );
} );
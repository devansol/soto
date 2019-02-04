$(document).ready(function(){
	 // $('#tbl-data').DataTable();
	$('#txt-arti').prop('disabled', true).css({"background-color" : "white"});
	var base_url = $('#base_url').val();
	$('#tbl-data').DataTable();
	console.log(base_url);
	$('#overlay-screen').hide(); 
	$('#btn-cari').on('click', function(){
		var kata = $('#inp-kata').val();
		cari_kata(kata);
	});

	$('input[type=search]').css({"border" : "0", "border-bottom" : "1px solid grey"})

	$('#btn-tambah').on('click', function(){
		window.location.href = base_url + "controller_home/view_insert";
	});

	$('#btn-reset').on('click', function(){
		$('#inp-kata').val("");
		$('#txt-arti').val("");
	});

	$('#btn-hapus-data').on('click', function(){
		alert('Anda yakin ingin menghapus ? ', function(){
			alert('yes');
		});
	});

	$('#inp-kata').on('keypress', function(e){
		if(e.which == 13) {
			var kata = $('#inp-kata').val();
			cari_kata(kata);
		}
	});	

	get_data();

	$('#btn-ubah').on('click', function(){
		var id = $('#inp-id').val();
		var kata = $('#inp-insert-kata').val();
		var keterangan = $('#inp-keterangan-kata').val();
		if(kata.length == 0 || keterangan.length == 0) {
			alert('Form input tidak boleh ada yang kosong !');
		}else{
			update_kata(id,kata,keterangan);
		}
	});

	$('#btn-simpan').on('click', function(){
		var kata = $('#inp-insert-kata').val();
		var keterangan = $('#inp-keterangan-kata').val();
		if(kata.length == 0 || keterangan.length == 0) {
			alert('Form input tidak boleh ada yang kosong !');
		}else{
			insert_kata(kata,keterangan);
		}

	});

	$('#btn-kembali').on('click', function(){
		window.location.href = base_url;
	});

	$('#btn-edit').on('click', function(){
		window.location.href = base_url + "controller_home/view_update";
	});
	
	function insert_kata(kata, keterangan){
		$.ajax({
			url: base_url + "controller_home/insert_data",
			data: {
				kata,
				keterangan
			},
			type : "POST",
			success : function(response, status, error) {
				try{
					if(response == true) {
						alert('Kata berhasil di tambahkan !');
						window.location.href = base_url;
					}else{
						alert('Kata gagal di tambahkan');
					}
					console.log(response);
				}catch (e) {
					alert(e.message);
				}
			},
			error : function(response,status,error) {
				console.log(response);
			}
		});
	}

	function update_kata(id, kata, keterangan){
		$.ajax({
			url: base_url + "controller_home/update_kata",
			data: {
				id,
				kata,
				keterangan
			},
			type : "POST",
			success : function(response, status, error) {
				try{
					if(response == true) {
						alert('Kata berhasil di ubah !');
						window.location.href = base_url;
					}else{
						alert('Kata gagal di ubah');
					}
					console.log(response);
				}catch (e) {
					alert(e.message);
				}
			},
			error : function(response,status,error) {
				console.log(response);
			}
		});
	}

	function cari_kata(kata){
		$.ajax({
			url: base_url + "controller_home/cari_kata",
			data: {
				kata
			},
			type : "POST",
			success : function(response, status, error) {
				try{
					console.log(response);
					if(response.length == 0) {
						alert('Kata : '+kata.toUpperCase()+' tidak ditemukan !')
					}else{
						var nama_kata = "";
						var keterangan = "";
						var feed = "";
						var gabung = "";
						$.each(response, function() {
							nama_kata = this['nama_kata'],
							keterangan = this['keterangan'],
							gabung = nama_kata +" = "+keterangan,
							feed = feed + gabung + "\n";
						});
					
					}
					$('#txt-arti').val(feed).prop('disabled', true);
				}catch (e) {
					alert(e.message);
				}
			},
			error : function(response,status,error) {
				console.log(response);
			}
		});
	}

	function get_data(){
		$.ajax({
			url: base_url + "controller_home/get_data",
			data: {},
			type : "POST",
			async : false,
			success : function(response, status, error) {
				try{
					$.each(response, function() {
						$('<option id="option-list"/>').val(this['nama_kata'].toUpperCase()).appendTo('#datalist-kata');
					})
				}catch (e) {
					alert(e.message);
				}
			},
			error : function(response,status,error) {
				console.log(response);
			}
		});
	}
});
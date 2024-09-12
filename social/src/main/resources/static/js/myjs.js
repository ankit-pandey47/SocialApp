

const toggleAlert = () => {
    if ($(".alert-container").is(":visible")) {
        // If sidebar is visible, hide it
        $(".alert-container").hide();
       
    } else {
        // If sidebar is hidden, show it
        $(".alert-container").show();
       
    }
}




const searchkro=()=>{
    
     let query = $("#search-input").val();

     

     if(query==""){
        $(".search-result").hide();
     }
     
     else{

        console.log(query)
        let url = `http://localhost:8080/user/process-search/${query}` 
        fetch(url)
        .then(response =>{
            return response.json();
        }).then(data=>{

            let text = `<div class='list-group'>`;

            data.forEach(users => {
               text += `<div class="list-group-item list-group-item-action d-flex align-items-center">
                        <img src="/images/${users.profile_img}" style="height: 35px; width: 35px; border-radius: 50%; margin-right: 10px;" />
                        <a href='/user/friend-profile/${users.id}'  style="text-decoration:none;">${users.username}</a>
                    </div>`;
            });

            text += `</div>`;
            $(".search-result").html(text);
            $(".search-result").show();
    

            
        });

        
     }
}

///when we start filling username in signup remove the username already exist msg
const removemsg=()=>{
    $(".warn-msg").hide();
}


function previewImage(event) {
	    const file = event.target.files[0];  // Get the file from the input
	    const reader = new FileReader();  // Create a FileReader object

	    reader.onload = function() {
	        const imagePreview = document.getElementById('image-preview');
	        imagePreview.src = reader.result;  // Set the image preview source to the file data
	        imagePreview.style.display = 'block';  // Make the image preview visible
	    }

	    if (file) {
	        reader.readAsDataURL(file);  // Read the image as a data URL
	    }
	}


<template>
  <div class="login-page">
      <Navbar />
    <b-row align-h="center">
      <h2 class="shareArt"></h2>
      <!-- <img src="../components/artImage.jpg" width="50" height="50"/> -->
    </b-row>
    <b-row align-h="center">
      <form>

        <div class="form-group">
          <label for="file" class="mb-0">Attachments</label><br />
          <input class='form-control' @change="previewImage" accept="image/*" type="file" name="file" id="file" multiple /><br/>
        </div>

        <div v-if="imageData!=null">                     
          <img class="preview" height="268" width="356" :src="img1">
       <br>
        </div>
        <div class="form-group">
          <label for="title" class="mb-0 mt-1">Title</label><br />
        <input class='form-control' type="text" id="title" name="title" multiple /><br />
        </div>
        <div class="form-group">
          <label for="price" class="mb-0 mt-1">Price</label><br />
          <input class='form-control' type="number" id="price" name="price" multiple /><br />
        </div>

        <div class="form-group">
          <label for="descripion" class="mb-0 mt-1">Description</label><br />
          <textarea class='form-control' id="description" name="description" rows="5" cols="23" multiple /><br />
        </div>

        <div class="form-group">
          <label for="collection" class="mb-0 mt-1">Collection</label><br />
          <input class='form-control' type="text" id="collection" name="collection" multiple /><br />
        </div>

        <div class="form-group">
          <label for="onpremises" class="mb-0 mt-1">On Premises</label>
          <input class='form-control' type="checkbox" id="onpremises" name="onpremises" multiple /><br />
        </div>

        <div class="form-group">
          <label for="email" class="mb-0 mt-1">Email Address</label><br />
          <input class='form-control' type="email" id="email" name="email" multiple /><br />
        </div> 

        <input type="submit" value="Upload Artwork" />
      </form>
    </b-row>
  </div>
</template>

<script>
import Navbar from '../components/Navbar'
import firebase from 'firebase';
export default {
  
  components: { Navbar },
  name: 'Login',
  data () {
    return {
      caption : '',
      img1: '',
      imageData: null,
      title: '',
      price: -1,
      description: '',
      collection: '',
      isOnPremise: false,
    }
  },
 methods:{
   create () {
      
      const post = {
        photo: this.img1,
        caption: this.caption        
      }
      firebase.database().ref('PhotoGallery').push(post)
      .then((response) => {
        console.log(response)
      })
      .catch(err => {
        console.log(err)
      })
    },
    previewImage(event) {
      this.uploadValue=0;
      this.img1=null;
      this.imageData = event.target.files[0];
      this.onUpload()
    },
    onUpload(){
      this.img1=null;
      const storageRef=firebase.storage().ref(`${this.imageData.name}`).put(this.imageData);
      storageRef.on(`state_changed`,snapshot=>{
      this.uploadValue = (snapshot.bytesTransferred/snapshot.totalBytes)*100;
        }, error=>{console.log(error.message)},
      ()=>{this.uploadValue=100;
          storageRef.snapshot.ref.getDownloadURL().then((url)=>{
              this.img1 =url;
              console.log(this.img1)
            });
          }      
        );
    },

 }
 
  
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
td {
  padding: 2px;
  text-align: left;
}
td.button {
  padding: 5px;
  text-align: center;
}
span {
  padding: 10px;
}
h2.shareArt {
  padding-bottom: 20px;
}
div#app {
  margin-top: 10px;
}
</style>

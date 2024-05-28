console.log("Hello")


let currentTheme=getCurrentTheme()

changeTheme()

document.querySelector("#theme_text").textContent=currentTheme=="dark"?"Light":"Dark";
function changeTheme()
{
    document.querySelector("html").classList.add(currentTheme);
    const changeThemeButton=document.querySelector("#theme_change_button");

    changeThemeButton.addEventListener("click",()=>{
        console.log("change");
        let oldTheme=currentTheme;
        
        if(currentTheme=="light"){
         currentTheme="dark";
        }
        else{
         currentTheme="light";
        }

        document.querySelector("html").classList.remove(oldTheme);
        document.querySelector("html").classList.add(currentTheme);
        
        setTheme(currentTheme);

        changeThemeButton.querySelector("span").textContent=currentTheme=="dark"?"Light":"Dark";

    });
}

function setTheme(theme)
{
   localStorage.setItem("theme",theme);
}

function getCurrentTheme()
{
    let theme=localStorage.getItem("theme");

    return theme ? theme : "light";

}

// fetch("http://localhost:9090/v1/api/view")
// .then(response => response.json())
// .then(data => console.log(data))
// .catch(error => console.error('Error:', error));
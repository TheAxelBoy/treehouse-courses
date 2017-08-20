document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('registrar');
  const input = form.querySelector('input');
  const ul = document.getElementById('invitedList');
  const filterCheckBox = document.getElementById('filter');
  const messageBox = document.getElementById('messageBox');
  const message = document.getElementById('message');
  const closeMessage = document.querySelector('#closeMessage > img')
  
  // Filter guest which aren't confirmed
  filterCheckBox.addEventListener('change', (e) => {
    const isChecked = e.target.checked;
    const lis = ul.children;
    
    if (isChecked) {
      for (let i = 0; i < lis.length; i += 1) {
        let li = lis[i];
        if (li.className === 'responded') {
          li.querySelector('label').style.display = 'none';
          li.style.display = '';
        } else {
          li.style.display = 'none';
        }
      }
    } else {
      for (let i = 0; i < lis.length; i += 1) {
        let li = lis[i];
        li.querySelector('label').style.display = '';
        li.style.display = '';
      }
    }
  });
  
  // Display a message 
  function displayMessage(text) {
    message.textContent = text;
    messageBox.style.display = 'flex';
  }

  // Close message
  closeMessage.addEventListener('click', () => {
    messageBox.style.display = 'none';
  });

  // Find specific text node that contains a substring
  // replace its content with text
  function changeTextNode(node, substring, text) {
    for(let i = 0; i < node.childNodes.length; i++) {
      let currentNode = node.childNodes[i];
      if (currentNode.nodeType == Node.TEXT_NODE &&
          currentNode.textContent.indexOf(substring) != -1) {
        return currentNode.textContent = text;
      }
    }
  };

  // Create guest
  function createLI(text) {
    // Helper functions
    function createElement(elementName, property, value) {
      const element = document.createElement(elementName);
      element[property] = value;
      return element;
    }
    
    function appendToLI(elementName, property, value) {
      const element = createElement(elementName, property, value);
      li.appendChild(element);
      return element;
    }
      
    // Create list item with name and append elements
    const li = document.createElement('li');
    appendToLI('span', 'textContent', text);   
    appendToLI('label', 'textContent', 'Confirm')
      .appendChild(createElement('input', 'type', 'checkbox'));   
    appendToLI('button', 'textContent', 'edit');
    appendToLI('button', 'textContent', 'remove');
    
    return li;
  }
  
  // Add guest - submit
  form.addEventListener('submit', (e) => {
    e.preventDefault();
    const text = input.value;

    if (text != '') {
      if (text == 'test') {
        displayMessage('The guest already exists, please choose another name!');
      } else {
        message.style.display = 'none';
        input.value = '';
        const li = createLI(text);
        ul.appendChild(li);
      }
    } else {
      displayMessage('Please type in a name!');
    }
  });
  
  // Confirm guest - checkbox
  ul.addEventListener('change', (e) => {
    const checkbox = e.target;
    const checked = checkbox.checked;
    const listItem = checkbox.parentNode.parentNode;
    
    if (checked) {
      changeTextNode(listItem.querySelector('label'), 'Confirm', 'Confirmed');
      listItem.className = 'responded';
    } else {
      changeTextNode(listItem.querySelector('label'), 'Confirmed', 'Confirm');
      listItem.className = '';
    }
  });
  
  // Click handler for buttons
  ul.addEventListener('click', (e) => {
    if (e.target.tagName === 'BUTTON') {
      const button = e.target;
      const li = e.target.parentNode;
      const ul = li.parentNode;
      const action = button.textContent;
      
      const nameActions = {
        remove: () => {
          ul.removeChild(li);
        },
        edit: () => {
          const span = li.firstElementChild;
          const input = document.createElement('input');
          input.type = 'text';
          input.value = span.textContent;
          li.insertBefore(input, span);
          li.removeChild(span);
          button.textContent = 'save';
        },
        save: () => {
          const input = li.firstElementChild;
          const span = document.createElement('span');
          span.textContent = input.value;
          li.insertBefore(span, input);
          li.removeChild(input);
          button.textContent = 'edit';
        }
      }

      // Select and run action in button's name
      nameActions[action]();
    }
  });
});
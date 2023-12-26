const core = require('@actions/core');
const github = require('@actions/github');

try {
  // `who-to-greet` input defined in action metadata file
  const nameToGreet = core.getInput('who-to-greet');
  console.log(`Hello ${nameToGreet}!`);

  const args = process.argv.slice(2); // The first two elements are 'node' and the script name


  if (args.length === 0) {
    console.error('Error: Please provide a value as a command line argument.');
    process.exit(1); // Exit with an error code
  }

  const inputValue = args[0]; // Assuming you want the first argument

  console.log(`Input Value from Command Line: ${inputValue}`);
  
  const time = (new Date()).toTimeString();
  core.setOutput("time", time);
  // Get the JSON webhook payload for the event that triggered the workflow
  const payload = JSON.stringify(github.context.payload, undefined, 2)
  //console.log(`The event payload: ${payload}`);
} catch (error) {
  core.setFailed(error.message);
}

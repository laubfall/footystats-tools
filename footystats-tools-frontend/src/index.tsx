import * as React from "react";
import App from "./components/App";
import { createRoot } from "react-dom/client";
import { initialize } from "./i18n/translate";

initialize(new Intl.Locale(navigator.language)).then(() => {
	const container = document.getElementById("root");
	const root = createRoot(container);
	root.render(<App />);
});

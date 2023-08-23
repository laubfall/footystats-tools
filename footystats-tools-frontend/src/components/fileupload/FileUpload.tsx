import React, { useRef } from "react";
import { FootyStatsCsvUploadControllerApi } from "../../footystats-frontendapi";
import { useFileUpload } from "../../react-use-file-upload/useFileUpload";
import { Button, Card, Col, Row } from "react-bootstrap";
import { Messages } from "../alert/Messages";

export const FileUpload = () => {
	const {
		files,
		fileNames,
		fileTypes,
		totalSize,
		totalSizeInBytes,
		handleDragDropEvent,
		clearAllFiles,
		createFormData,
		setFiles,
		removeFile,
	} = useFileUpload();

	const inputRef = useRef<HTMLInputElement>();

	const [messages, setMessages] = React.useState<string[]>([]); // ["jfdklsjf"

	const handleSubmit = async (e) => {
		e.preventDefault();

		const formData = createFormData();
		const files: Blob[] = [];

		formData.forEach((val) => files.push(val as File));

		new FootyStatsCsvUploadControllerApi()
			.uploadMultipleFiles({
				files,
			})
			.then((response) => {
				setMessages(["Successfully submitted files."]);
			})
			.catch((error) => {
				setMessages(["Failed to submit files."]);
				console.error("Failed to submit files.", error);
			});
	};

	return (
		<>
			<Messages messages={messages} />
			<Row>
				<h1>Upload Files</h1>

				<p>
					Please use the form to your right to upload any file(s) of
					your choosing.
				</p>

				<div className="form-container">
					{/* Display the files to be uploaded */}
					<div>
						<ul>
							{fileNames.map((name) => (
								<li key={name}>
									<span>{name}</span>

									<span onClick={() => removeFile(name)}>
										<i className="fa fa-times" />
									</span>
								</li>
							))}
						</ul>

						{files.length > 0 && (
							<ul>
								<li>
									File types found: {fileTypes.join(", ")}
								</li>
								<li>Total Size: {totalSize}</li>
								<li>Total Bytes: {totalSizeInBytes}</li>

								<li className="clear-all">
									<Button onClick={() => clearAllFiles()}>
										Clear All
									</Button>
								</li>
							</ul>
						)}
					</div>

					{/* Provide a drop zone and an alternative button inside it to upload files. */}
					<Card
						body
						onDragEnter={handleDragDropEvent}
						onDragOver={handleDragDropEvent}
						onDrop={(e) => {
							handleDragDropEvent(e);
							setFiles(e, "a");
						}}
					>
						<p>Drag and drop files here</p>

						<Button onClick={() => inputRef.current?.click()}>
							Or select files to upload
						</Button>

						{/* Hide the crappy looking default HTML input */}
						<input
							ref={inputRef}
							type="file"
							multiple
							style={{ display: "none" }}
							onChange={(e) => {
								setFiles(e, "a");
								inputRef.current.value = null;
							}}
						/>

						<Button className={"m-lg-2"} onClick={handleSubmit}>
							Submit
						</Button>
					</Card>
				</div>
			</Row>
		</>
	);
};

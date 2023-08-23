import React from "react";
import { FileUpload } from "../../components/fileupload/FileUpload";

export default {
	title: "FileUpload/FileUpload",
	parameters: {
		mockData: [
			{
				url: "http://localhost:8080/uploadMultipleFiles",
				method: "POST",
				status: 200,
				response: [
					{
						data: "Hello storybook-addon-mock!",
					},
				],
			},
		],
	},
};

const Template = () => {
	return (
		<>
			<FileUpload />
		</>
	);
};

export const FileUploadStory = Template.bind({});
